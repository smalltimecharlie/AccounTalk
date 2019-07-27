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
import { IShares } from 'app/shared/model/shares.model';
import { getEntities as getShares } from 'app/entities/shares/shares.reducer';
import { getEntity, updateEntity, createEntity, reset } from './dividends-received.reducer';
import { IDividendsReceived } from 'app/shared/model/dividends-received.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDividendsReceivedUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDividendsReceivedUpdateState {
  isNew: boolean;
  taxReturnId: string;
  sharesId: string;
}

export class DividendsReceivedUpdate extends React.Component<IDividendsReceivedUpdateProps, IDividendsReceivedUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      taxReturnId: '0',
      sharesId: '0',
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
    this.props.getShares();
  }

  saveEntity = (event, errors, values) => {
    values.paymentDate = convertDateTimeToServer(values.paymentDate);

    if (errors.length === 0) {
      const { dividendsReceivedEntity } = this.props;
      const entity = {
        ...dividendsReceivedEntity,
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
    this.props.history.push('/entity/dividends-received');
  };

  render() {
    const { dividendsReceivedEntity, taxReturns, shares, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.dividendsReceived.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.dividendsReceived.home.createOrEditLabel">Create or edit a DividendsReceived</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : dividendsReceivedEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="dividends-received-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="dividends-received-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="paymentDateLabel" for="dividends-received-paymentDate">
                    <Translate contentKey="accounTalkApp.dividendsReceived.paymentDate">Payment Date</Translate>
                  </Label>
                  <AvInput
                    id="dividends-received-paymentDate"
                    type="datetime-local"
                    className="form-control"
                    name="paymentDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.dividendsReceivedEntity.paymentDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="amountReceivedLabel" for="dividends-received-amountReceived">
                    <Translate contentKey="accounTalkApp.dividendsReceived.amountReceived">Amount Received</Translate>
                  </Label>
                  <AvField id="dividends-received-amountReceived" type="string" className="form-control" name="amountReceived" />
                </AvGroup>
                <AvGroup>
                  <Label for="dividends-received-taxReturn">
                    <Translate contentKey="accounTalkApp.dividendsReceived.taxReturn">Tax Return</Translate>
                  </Label>
                  <AvInput id="dividends-received-taxReturn" type="select" className="form-control" name="taxReturn.id">
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
                  <Label for="dividends-received-shares">
                    <Translate contentKey="accounTalkApp.dividendsReceived.shares">Shares</Translate>
                  </Label>
                  <AvInput id="dividends-received-shares" type="select" className="form-control" name="shares.id">
                    <option value="" key="0" />
                    {shares
                      ? shares.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/dividends-received" replace color="info">
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
  shares: storeState.shares.entities,
  dividendsReceivedEntity: storeState.dividendsReceived.entity,
  loading: storeState.dividendsReceived.loading,
  updating: storeState.dividendsReceived.updating,
  updateSuccess: storeState.dividendsReceived.updateSuccess
});

const mapDispatchToProps = {
  getTaxReturns,
  getShares,
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
)(DividendsReceivedUpdate);
