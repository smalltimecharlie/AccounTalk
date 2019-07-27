import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pension-received.reducer';
import { IPensionReceived } from 'app/shared/model/pension-received.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPensionReceivedDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PensionReceivedDetail extends React.Component<IPensionReceivedDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { pensionReceivedEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.pensionReceived.detail.title">PensionReceived</Translate> [
            <b>{pensionReceivedEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="grossPensionReceived">
                <Translate contentKey="accounTalkApp.pensionReceived.grossPensionReceived">Gross Pension Received</Translate>
              </span>
            </dt>
            <dd>{pensionReceivedEntity.grossPensionReceived}</dd>
            <dt>
              <span id="taxDeducted">
                <Translate contentKey="accounTalkApp.pensionReceived.taxDeducted">Tax Deducted</Translate>
              </span>
            </dt>
            <dd>{pensionReceivedEntity.taxDeducted}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.pensionReceived.taxReturn">Tax Return</Translate>
            </dt>
            <dd>{pensionReceivedEntity.taxReturn ? pensionReceivedEntity.taxReturn.id : ''}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.pensionReceived.pensionProvider">Pension Provider</Translate>
            </dt>
            <dd>{pensionReceivedEntity.pensionProvider ? pensionReceivedEntity.pensionProvider.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/pension-received" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/pension-received/${pensionReceivedEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ pensionReceived }: IRootState) => ({
  pensionReceivedEntity: pensionReceived.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PensionReceivedDetail);
