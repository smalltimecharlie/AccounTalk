import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBankDetails } from 'app/shared/model/bank-details.model';
import { getEntities as getBankDetails } from 'app/entities/bank-details/bank-details.reducer';
import { IStatePensionReceived } from 'app/shared/model/state-pension-received.model';
import { getEntities as getStatePensionReceiveds } from 'app/entities/state-pension-received/state-pension-received.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntity, updateEntity, createEntity, reset } from './tax-return.reducer';
import { ITaxReturn } from 'app/shared/model/tax-return.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITaxReturnUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITaxReturnUpdateState {
  isNew: boolean;
  bankRefundDetailsId: string;
  statePensionDetailsId: string;
  clientId: string;
}

export class TaxReturnUpdate extends React.Component<ITaxReturnUpdateProps, ITaxReturnUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      bankRefundDetailsId: '0',
      statePensionDetailsId: '0',
      clientId: '0',
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

    this.props.getBankDetails();
    this.props.getStatePensionReceiveds();
    this.props.getClients();
  }

  saveEntity = (event, errors, values) => {
    values.year = convertDateTimeToServer(values.year);

    if (errors.length === 0) {
      const { taxReturnEntity } = this.props;
      const entity = {
        ...taxReturnEntity,
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
    this.props.history.push('/entity/tax-return');
  };

  render() {
    const { taxReturnEntity, bankDetails, statePensionReceiveds, clients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.taxReturn.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.taxReturn.home.createOrEditLabel">Create or edit a TaxReturn</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : taxReturnEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="tax-return-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="tax-return-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="yearLabel" for="tax-return-year">
                    <Translate contentKey="accounTalkApp.taxReturn.year">Year</Translate>
                  </Label>
                  <AvInput
                    id="tax-return-year"
                    type="datetime-local"
                    className="form-control"
                    name="year"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.taxReturnEntity.year)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="studentLoanLabel" for="tax-return-studentLoan">
                    <Translate contentKey="accounTalkApp.taxReturn.studentLoan">Student Loan</Translate>
                  </Label>
                  <AvInput
                    id="tax-return-studentLoan"
                    type="select"
                    className="form-control"
                    name="studentLoan"
                    value={(!isNew && taxReturnEntity.studentLoan) || 'NONE'}
                  >
                    <option value="NONE">{translate('accounTalkApp.StudentLoan.NONE')}</option>
                    <option value="PLAN1">{translate('accounTalkApp.StudentLoan.PLAN1')}</option>
                    <option value="PLAN2">{translate('accounTalkApp.StudentLoan.PLAN2')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="tax-return-bankRefundDetails">
                    <Translate contentKey="accounTalkApp.taxReturn.bankRefundDetails">Bank Refund Details</Translate>
                  </Label>
                  <AvInput id="tax-return-bankRefundDetails" type="select" className="form-control" name="bankRefundDetails.id">
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
                <AvGroup>
                  <Label for="tax-return-statePensionDetails">
                    <Translate contentKey="accounTalkApp.taxReturn.statePensionDetails">State Pension Details</Translate>
                  </Label>
                  <AvInput id="tax-return-statePensionDetails" type="select" className="form-control" name="statePensionDetails.id">
                    <option value="" key="0" />
                    {statePensionReceiveds
                      ? statePensionReceiveds.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="tax-return-client">
                    <Translate contentKey="accounTalkApp.taxReturn.client">Client</Translate>
                  </Label>
                  <AvInput id="tax-return-client" type="select" className="form-control" name="client.id">
                    <option value="" key="0" />
                    {clients
                      ? clients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/tax-return" replace color="info">
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
  bankDetails: storeState.bankDetails.entities,
  statePensionReceiveds: storeState.statePensionReceived.entities,
  clients: storeState.client.entities,
  taxReturnEntity: storeState.taxReturn.entity,
  loading: storeState.taxReturn.loading,
  updating: storeState.taxReturn.updating,
  updateSuccess: storeState.taxReturn.updateSuccess
});

const mapDispatchToProps = {
  getBankDetails,
  getStatePensionReceiveds,
  getClients,
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
)(TaxReturnUpdate);
