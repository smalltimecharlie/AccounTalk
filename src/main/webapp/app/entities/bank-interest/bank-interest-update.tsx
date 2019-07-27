import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { getEntities as getTaxReturns } from 'app/entities/tax-return/tax-return.reducer';
import { IBankDetails } from 'app/shared/model/bank-details.model';
import { getEntities as getBankDetails } from 'app/entities/bank-details/bank-details.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bank-interest.reducer';
import { IBankInterest } from 'app/shared/model/bank-interest.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBankInterestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBankInterestUpdateState {
  isNew: boolean;
  taxReturnId: string;
  bankdetailsId: string;
}

export class BankInterestUpdate extends React.Component<IBankInterestUpdateProps, IBankInterestUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      taxReturnId: '0',
      bankdetailsId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getTaxReturns();
    this.props.getBankDetails();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { bankInterestEntity } = this.props;
      const entity = {
        ...bankInterestEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/bank-interest');
  };

  render() {
    const { bankInterestEntity, taxReturns, bankDetails, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.bankInterest.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.bankInterest.home.createOrEditLabel">Create or edit a BankInterest</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : bankInterestEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="bank-interest-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="bank-interest-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="netInterestLabel" for="bank-interest-netInterest">
                    <Translate contentKey="accounTalkApp.bankInterest.netInterest">Net Interest</Translate>
                  </Label>
                  <AvField id="bank-interest-netInterest" type="string" className="form-control" name="netInterest" />
                </AvGroup>
                <AvGroup>
                  <Label id="taxDeductedLabel" for="bank-interest-taxDeducted">
                    <Translate contentKey="accounTalkApp.bankInterest.taxDeducted">Tax Deducted</Translate>
                  </Label>
                  <AvField id="bank-interest-taxDeducted" type="string" className="form-control" name="taxDeducted" />
                </AvGroup>
                <AvGroup>
                  <Label for="bank-interest-taxReturn">
                    <Translate contentKey="accounTalkApp.bankInterest.taxReturn">Tax Return</Translate>
                  </Label>
                  <AvInput id="bank-interest-taxReturn" type="select" className="form-control" name="taxReturn.id">
                    <option value="" key="0" />
                    {taxReturns
                      ? taxReturns.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="bank-interest-bankdetails">
                    <Translate contentKey="accounTalkApp.bankInterest.bankdetails">Bankdetails</Translate>
                  </Label>
                  <AvInput id="bank-interest-bankdetails" type="select" className="form-control" name="bankdetails.id">
                    <option value="" key="0" />
                    {bankDetails
                      ? bankDetails.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/bank-interest" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  taxReturns: storeState.taxReturn.entities,
  bankDetails: storeState.bankDetails.entities,
  bankInterestEntity: storeState.bankInterest.entity,
  loading: storeState.bankInterest.loading,
  updating: storeState.bankInterest.updating,
  updateSuccess: storeState.bankInterest.updateSuccess
});

const mapDispatchToProps = {
  getTaxReturns,
  getBankDetails,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BankInterestUpdate);
